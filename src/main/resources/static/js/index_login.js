document.querySelector('#login').addEventListener('click', login);
// If user already logged in then move to dashboard [onload]

function login(evt) {

    let loginBtn = document.querySelector("#login");
    loginBtn.innerHTML = "";

    let spinner = document.createElement("span");
    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    spinner.setAttribute("role","status");
    spinner.setAttribute("aria-hidden","true");
    loginBtn.appendChild(spinner);

    let loadingText = document.createTextNode("Loading...");
    loginBtn.appendChild(loadingText);

    evt.preventDefault();
    let un = document.querySelector('#username').value;
    let pw = document.querySelector('#password').value;

    let data = {
        username: un,
        password: pw
    };

    fetch('http://localhost:7000/login', {
        method: 'POST',
        credentials: 'include', // this specifies that when you receive cookies, you should include them in future requests. So in our case, it's important so that the backend can identify if we are logged in or not.
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((response) => {
        if (response.status === 200) {
            return response.json();
        } else if (response.status === 401) {
            displayInvalidLogin();
        }
        return response.json();
    }).then((data) => {
        console.log(data);
        if(data.roleId.ersUserRoleId === 2){
            window.location.href = '/dash_submitter.html';
        } 
        else {
            // window.location.href = '/index.html';
            window.location.href = '/dash_approver.html';
        }
    })
}

function displayInvalidLogin() {
    let bodyElement = document.querySelector('#error-div');
    bodyElement.innerHTML = '';

    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.innerHTML = 'Invalid login!';

    bodyElement.appendChild(pElement);
}
