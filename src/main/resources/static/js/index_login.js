document.querySelector('#login').addEventListener('click', login);

function login(evt) {
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
        alert("reached here");
        if (response.status === 200) {
            // console.log(response);
            // alert(response.json);
            // window.location.href = '/landing.html';
            window.location.href = '/dash_submitter.html';
            // window.location.href = response.redirect;
        } else if (response.status === 401) {
            displayInvalidLogin();
        }
    })
}

function displayInvalidLogin() {
    let bodyElement = document.querySelector('error-div');
    bodyElement.innerHTML = '';

    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.innerHTML = 'Invalid login!';

    bodyElement.appendChild(pElement);
}