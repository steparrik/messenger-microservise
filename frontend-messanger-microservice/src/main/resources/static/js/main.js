const registerForm = document.querySelector('#register-form');
const newDialog = document.querySelector("#new-dialog");
const newGroup = document.querySelector("#new-group");
const loginForm = document.querySelector('#login-form');
const chatList = document.getElementById('chat-list');
const  errorAuth = document.querySelector("#error")
const chatName = document.querySelector("#chatName");
const chatPage = document.querySelector("#chat-page");
const messageArea = document.querySelector("#messageArea");
const messageInput = document.querySelector("#messageForm");
const logout = document.querySelector("#logout-button");
const profilePage = document.querySelector("#profile");
const profileData = document.querySelector("#profileData")
let stompClient;
const currentPath = window.location.pathname;
const jwt = localStorage.getItem("jwt");
const error = document.querySelector("#error-name")



document.addEventListener('DOMContentLoaded', function (event) {
    if (!jwt && currentPath !== '/register.html' && currentPath !== '/auth.html') {
        window.location.href = '/auth.html';
    } else if (jwt && (currentPath === '/register.html' || currentPath === '/auth.html')) {
        window.location.href = '/chats.html';
    }


    if (chatList) {
        if (localStorage.getItem("jwt")) {
            fetchChats();
        } else {
            chatList.innerHTML = '<li>Вы не авторизованы</li>';
        }

        function fetchChats() {
            fetch('http://localhost:8085/messenger/chats', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt")
                }
            })
                .then(response => {
                    if (!response.ok) {
                        const listItem = document.createElement('li');
                        listItem.textContent = "Вы не авторизованы";
                        chatList.appendChild(listItem);
                        return;
                    }
                    return response.json();
                })
                .then(data => {
                    if (data) {
                        // Очистить текущий список чатов
                        chatList.innerHTML = '';
                        // Заполнить список новыми данными
                        data.forEach(chat => {
                            const listItem = document.createElement('li');
                            const chatLink = document.createElement('a');
                            chatLink.href = `chat.html?chatId=${chat.id}`;
                            chatLink.textContent = chat.name;
                            listItem.appendChild(chatLink);
                            chatList.appendChild(listItem);
                        });
                    }
                })
                .catch(error => console.error('Ошибка:', error));
        }
        event.preventDefault();
        return;
    }

    if(newDialog){
        newDialog.addEventListener("submit", async function (event) {
            const nameCompanion = document.querySelector("#nameCompanion").value
            console.log(nameCompanion)
            event.preventDefault()
            const response = await addAndGetNewDialog(nameCompanion);
            if(response===null){
                console.log("error")
            }else{
                window.location.href="chat.html?chatId="+response.id
            }
        })
        newGroup.addEventListener("submit", async function (event) {
            const nameGroup = document.querySelector("#nameGroup").value
            if (nameGroup === null || nameGroup === "") {
                return
            }
            const name = {
                name: nameGroup
            }
            event.preventDefault()
            const response = await addAndGetNewGroup(JSON.stringify(name
            ))
            if(response===null){
                console.log("error")
            }else{
                window.location.href="chat.html?chatId="+response.id

            }
        })
    }
    if (chatPage) {
        const currentUrl = window.location.href;
        const url = new URL(currentUrl);
        const chatId = url.searchParams.get('chatId');

        function scrollToBottom() {
            messageArea.scrollTop = messageArea.scrollHeight;
        }

        function onError(error) {
            const connectingElement = document.getElementById('connecting');
            connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
            connectingElement.style.color = 'red';
        }

        async function sendMessage(event) {
            event.preventDefault();
            const messageContent = messageInput.querySelector("#message").value.trim();
            if(messageContent.trim()===""){
                return
            }
            const currentUrl = window.location.href;
            const url = new URL(currentUrl);
            const chatId = url.searchParams.get('chatId');

            const message = await sendMessageOnServer(chatId, messageContent)

            if (messageContent && stompClient && stompClient.connected) {
                stompClient.send("/app/chat.sendMessage/"+chatId, {}, JSON.stringify(message));
            }
            messageInput.querySelector("#message").value = '';
        }

        function onConnected() {
            const currentUrl = window.location.href;
            const url = new URL(currentUrl);
            const chatId = url.searchParams.get('chatId');
            // Subscribe to the Public Topic
            stompClient.subscribe('/topic/public/'+chatId, onMessageReceived);
        }

         function onMessageReceived(payload) {
            const message = JSON.parse(payload.body);
            const jwtPayload = parseJwt(localStorage.getItem("jwt"));
            const jwtUsername = jwtPayload.sub

             const listItem = document.createElement('li');
             const messageText = document.createElement('div');
             if(jwtUsername!==message.sender.username){
                 listItem.classList = "message left"
             }else{
                 listItem.classList = "message right"
             }

             messageText.textContent =
                 message.messageText;

             const date = new Date(message.timestamp);
             let hours = date.getHours();
             let minutes = date.getMinutes();
             minutes = minutes < 10 ? '0' + minutes : minutes;
             hours = hours < 10 ? '0' + hours : hours;
             const time = hours + ':' + minutes;

             messageText.className = 'message-text';

             const messageTime = document.createElement('div');
             messageTime.className = 'message-time';
             messageTime.textContent = time;

             listItem.appendChild(messageText);
             listItem.appendChild(messageTime);
             messageArea.appendChild(listItem);
            scrollToBottom()

        }

        function connect() {
            const socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);
        }

        fetch(`http://localhost:8085/messenger/chats/` + chatId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("jwt")
            }
        })
            .then(response => {
                if (!response.ok) {
                    const listItem = document.createElement('li');
                    listItem.textContent = "Вы не авторизованы";
                    chatList.appendChild(listItem);
                    return;
                }
                return response.json();
            })
            .then(data => {
                if (data) {
                    console.log(data);
                    const name = document.createElement('h1');
                    name.textContent = data.name.trim();
                    chatName.appendChild(name);
                    // Очистить текущий список чатов
                    messageArea.innerHTML = '';
                    // Заполнить список новыми данными
                    data.messages.forEach(message => {
                        const jwtPayload = parseJwt(localStorage.getItem("jwt"));
                        const jwtUsername = jwtPayload.sub

                        const listItem = document.createElement('li');
                        const messageText = document.createElement('div');
                        if(jwtUsername!==message.sender.username){
                            listItem.classList = "message left"
                        }else{
                            listItem.classList = "message right"
                        }
                        messageText.textContent =
                            message.messageText;

                        const date = new Date(message.timestamp);
                        let hours = date.getHours();
                        let minutes = date.getMinutes();
                        minutes = minutes < 10 ? '0' + minutes : minutes;
                        hours = hours < 10 ? '0' + hours : hours;
                        const time = hours + ':' + minutes;

                        messageText.className = 'message-text';

                        const messageTime = document.createElement('div');
                        messageTime.className = 'message-time';
                        messageTime.textContent = time;

                        listItem.appendChild(messageText);
                        listItem.appendChild(messageTime);
                        messageArea.appendChild(listItem);
                    });
                    scrollToBottom()
                }
            });
        connect();
        messageInput.addEventListener("submit", sendMessage, true);
    }

    if(profilePage){
        async function profileFunc() {
            console.log("PROFILE")
            const user = await getProfile();

            const profileLi = document.createElement('li');

            const username = document.createElement("a");
            username.textContent = "Никнейм: "+user.username;

            const pN = document.createElement("a");
            pN.textContent = "Номер телефона: " + user.phoneNumber;

            const fN = document.createElement("a");
            fN.textContent = "Полное имя: " +user.fullName;

            console.log(user);

            profileLi.appendChild(username);
            profileLi.appendChild(document.createElement("br"))
            profileLi.appendChild(pN);
            profileLi.appendChild(document.createElement("br"))

            profileLi.appendChild(fN);

            profileData.appendChild(profileLi);

            profilePage.addEventListener("submit", function (event) {
                    localStorage.removeItem("jwt")
                }
            )
        }
        profileFunc()
    }

    if(registerForm){
        const errorRegister = document.querySelector("#error")
        registerForm.addEventListener("submit", function (event){
            const username= document.querySelector("#usernameR").value
            const password= document.querySelector("#passwordR").value
            const confirmPassword = document.querySelector("#passwordRepeatR").value
            const phoneNumber= document.querySelector("#phoneNumberR").value
            const fullName = document.querySelector("#fullNameR").value
            const RegisterData = {
                username: username,
                password: password,
                confirmPassword: confirmPassword,
                phoneNumber: phoneNumber,
                fullName: fullName
            }
            event.preventDefault();
            fetch('http://localhost:8084/messenger/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(RegisterData)
            })
                .then(async response => {
                    if (!response.ok) {
                        const errorMessage = await response.json()
                        console.log(errorMessage)
                        errorRegister.textContent = errorMessage.message
                    }else{
                        errorRegister.textContent = ""
                        window.location.href="auth.html"
                    }
                })

        })}


    if (loginForm) {
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault();
            const username = document.querySelector('#username').value;
            const password = document.querySelector('#password').value;

            const authEntity = {
                username: username,
                password: password
            };

            fetch('http://localhost:8084/messenger/auth', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(authEntity)
            })
                .then(response => {
                    if (!response.ok) {
                            errorAuth.textContent = "Неверный логин или пароль";
                            return
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data);
                    localStorage.setItem("jwt", data.token);
                    window.location.href="/chats.html"
                });
        });

    }

});

function getProfile() {
    return fetch('http://localhost:8084/messenger/profile', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("jwt")
        }
    })
        .then(response => {
            if (!response.ok) {
                const listItem = document.createElement('li');
                listItem.textContent = "Вы не авторизованы";
                chatList.appendChild(listItem);
                return;
            }
            return response.json();
        })
        .then(data => {
            return data
        });
}


function sendMessageOnServer(chatId, text) {
    const messageText = {
        messageText: text
    }
    return fetch('http://localhost:8085/messenger/chats/'+chatId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("jwt")
        },
        body: JSON.stringify(messageText)
    })
        .then(response => {
            if (!response.ok) {
                const listItem = document.createElement('li');
                listItem.textContent = "Вы не авторизованы";
                chatList.appendChild(listItem);
                return;
            }
            return response.json();
        })
        .then(data => {
            return data
        });
}

function addAndGetNewDialog(nameCompanion){
    return fetch('http://localhost:8085/messenger/chats' +
        '?username='+nameCompanion, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("jwt")
        }
    })
        .then(async response => {
            if (!response.ok) {
                const as = await response.json();
                console.log(as.message
                )
                error.textContent = as.message
                return null
            }
            return response.json()
        })
        .then(data => {
            return data
        });
}

async function addAndGetNewGroup(groupName){
    return fetch('http://localhost:8085/messenger/chats', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("jwt")
        },body:groupName
    })
        .then(async response => {
            if(!response.ok){
                const as = await response.json();
                console.log(as.message
                )
                error.textContent = as.message
                return null
            }else{
            return response.json()}
        })
        .then(data => {
            return data
        });
}

function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    console.log(jsonPayload)
    return JSON.parse(jsonPayload);
}
