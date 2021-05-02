document.querySelector("#logout-link").addEventListener('click', logout);
document.querySelector("#blue-card-link").addEventListener('click', modalTicketForm);
document.querySelector("#offcanvas-add-link").addEventListener('click', modalTicketForm);
// document.querySelector("#add_ticket_submit").addEventListener('click', submitAddTicket);

window.onload = function() {
    renderCurrentUser();
    renderCurrentUserData();
}

function renderCurrentUser() {
    fetch('http://localhost:7000/current_user', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            window.location.href = '/';
        }

        return response.json();
    }).then((data) => {
        let firstName = data.firstName;
        let lastName = data.lastName;

        let userInfoElement = document.querySelector('#dash_title');
        let preexistingHtml = userInfoElement.innerHTML;
        userInfoElement.innerHTML = `${preexistingHtml} for : ${firstName} ${lastName}`
    })
}

function renderCurrentUserData() {
    fetch('http://localhost:7000/populate_data', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            // window.location.href = '/';
            console.log("400 status error");
        }

        return response.json();
    }).then((data) => {
        // let id = data.id;
        // let username = data.username;
        // let password = data.password;
        console.log(data);
        // console.log(data[0]);
        // console.log("Before selecting table");
        // let dashTable = document.querySelector("#dashboard_table");
        // console.log("After selecting table");
        // console.log(dashTable);

        let tBody = document.querySelector("#dashboard_table_body");
        tBody.innerHTML = "";
        for (i in data){
            // console.log(data[i]);
            // console.log(data[i].reimbId);
            tr = document.createElement("tr");

            td1 = document.createElement("td");
            tdText1 = document.createTextNode(data[i].reimbId);
            td1.appendChild(tdText1);
            tr.appendChild(td1);

            td2 = document.createElement("td");
            tdText2 = document.createTextNode(data[i].reimbAmount);
            td2.appendChild(tdText2);
            tr.appendChild(td2);

            td3 = document.createElement("td");
            // date1 = new Date(data[i].reimbSubmitted);
            // tdText3 = document.createTextNode(date1.getUTCFullYear()+"/"+(date1.getUTCMonth()+1)+"/"+date1.getUTCDate());
            tdText3 = document.createTextNode(data[i].reimbSubmitted);
            td3.appendChild(tdText3);
            tr.appendChild(td3);

            td4 = document.createElement("td");
            if(data[i].reimbResolved===null){
                tdText4 = document.createTextNode("----/--/--");
            } else {
                // date2 = new Date(data[i].reimbResolved);
                // tdText4 = document.createTextNode(date2.getUTCFullYear()+"/"+(date2.getUTCMonth()+1)+"/"+date2.getUTCDate());
                tdText4 = document.createTextNode(data[i].reimbResolved);
            }
            // tdText4 = document.createTextNode("Not fetched right now");
            td4.appendChild(tdText4);
            tr.appendChild(td4);

            td5 = document.createElement("td");
            if(data[i].reimbResolver===null){
                tdText5 = document.createTextNode("**No Resolver");
            } else {
                tdText5 = document.createTextNode(data[i].reimbResolver.firstName+" "+data[i].reimbResolver.lastName);
            }
            // tdText5 = document.createTextNode("Not Set yet");
            td5.appendChild(tdText5);
            tr.appendChild(td5);

            td6 = document.createElement("td");
            tdText6 = document.createTextNode(data[i].reimbStatusId.reimbStatus);
            td6.appendChild(tdText6);
            tr.appendChild(td6);

            td7 = document.createElement("td");
            tdText7 = document.createTextNode(data[i].reimbTypeId.reimbType);
            td7.appendChild(tdText7);
            tr.appendChild(td7);

            tBody.appendChild(tr);
        }

        // let userInfoElement = document.querySelector('#dash_title');
        // let preexistingHtml = userInfoElement.innerHTML;
        // userInfoElement.innerHTML = `${preexistingHtml} . Username: ${username}, password: ${password}`
    })
}

function modalTicketForm() {
    // window.location.href = '/submit_reimbursement.html';
    // document.querySelector("#dashboard").style.display = "none";
    // document.querySelector("#add_ticket").style.display = "block";
    // console.log("modalTicketForm initialized");
    $('#add_modal').modal('show');
    let amount = document.querySelector('#add_amount');
    let type = document.querySelector('#add_type');
    amount.value = "";
    type.value = "";
    let divElement = document.querySelector('#add-result-display');
    divElement.style.display="none";
}

function submitAddTicket() {
    let amount = document.querySelector('#add_amount').value;
    let type = document.querySelector('#add_type').value;

    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy+'-'+mm+'-'+dd;
    console.log(today);
    
    let data = {
        reimbAmount: amount,
        reimbSubmitted: today,
        reimbStatusIdInt: 1,
        reimbTypeIdString: type
    };

    fetch('http://localhost:7000/add_reimbursement', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((response) => {
        if (response.status === 400) {
            displayBadParameterFail();
        } else if (response.status === 404){
            displayRetrieveFail();
        }
        // return response.json();
    }).then(() => {
        let divElement = document.querySelector('#add-result-display');
        divElement.style.display="block";
        divElement.innerHTML = '';

        let pElement = document.createElement('p');
        pElement.style.color = 'green';
        pElement.innerHTML = 'Reimbursement successfully submitted.';

        divElement.appendChild(pElement);
        renderCurrentUserData();
    })
}

function displayBadParameterFail() {
    let divElement = document.querySelector('#add-result-display');
    divElement.style.display="block";
    divElement.innerHTML = '';

    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.innerHTML = 'Invalid input found! Reimbursement add failed.';

    divElement.appendChild(pElement);
}

function displayRetrieveFail() {
    let divElement = document.querySelector('#add-result-display');
    divElement.style.display="block";
    divElement.innerHTML = '';

    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.innerHTML = 'Unable to retrieve this reimbursement from database.';

    divElement.appendChild(pElement);
}

function logout() {
    fetch('http://localhost:7000/logout', {
        method: 'GET',
        credentials: 'include', // this specifies that when you receive cookies, you should include them in future requests. So in our case, it's important so that the backend can identify if we are logged in or not.
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        if (response.status === 200) {
            window.location.href = '/index_login.html';
        } else {
            console.log("Unable to logout");
        }
    })
}