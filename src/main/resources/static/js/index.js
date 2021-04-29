document.querySelector('#login').addEventListener('click', gotoLoginPage);


function gotoLoginPage() {


    fetch('http://localhost:7000/login', {
        method: 'GET',
        credentials: 'include', // this specifies that when you receive cookies, you should include them in future requests. So in our case, it's important so that the backend can identify if we are logged in or not.
        headers: {
            'Content-Type': 'application/json'
        },
    })
}