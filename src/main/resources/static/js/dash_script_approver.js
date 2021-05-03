document.querySelector("#logout-link").addEventListener('click', logout);
//Filter eventlistners
document.querySelector("#filter-all").addEventListener('click', filterAll);
document.querySelector("#filter-submitted").addEventListener('click', filterSubmitted);
document.querySelector("#filter-pending").addEventListener('click', filterPending);
document.querySelector("#filter-approved").addEventListener('click', filterApproved);
document.querySelector("#filter-paid").addEventListener('click', filterPaid);
document.querySelector("#filter-denied").addEventListener('click', filterDenied);

window.onload = function() {
    checkForApprover();
    renderCurrentUser();
    renderLastReimbursementData();
}

let reimbursementData;

function checkForApprover() {
    fetch('http://localhost:7000/check_for_approver', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 403 || response.status === 400) {
            window.location.href = '/';
        }
    })
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
        userInfoElement.innerHTML = `Manager ${preexistingHtml} for : ${firstName} ${lastName}`
    })
}

function renderLastReimbursementData() {
    fetch('http://localhost:7000/latest_approver_data', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            // window.location.href = '/';
            console.log("400 status error");
        }

        return response.json();
    }).then((data) => {
        reimbursementData = data;
        // let id = data.id;
        // let username = data.username;
        // let password = data.password;
        console.log(data);
        // console.log(data[0]);
        // console.log("Before selecting table");
        // let dashTable = document.querySelector("#dashboard_table");
        // console.log("After selecting table");
        // console.log(dashTable);
        populateDashboardTable(reimbursementData);
        // let userInfoElement = document.querySelector('#dash_title');
        // let preexistingHtml = userInfoElement.innerHTML;
        // userInfoElement.innerHTML = `${preexistingHtml} . Username: ${username}, password: ${password}`
    })
}

function filterAll(){
    populateDashboardTable(reimbursementData);
}

function filterSubmitted(){
    let submittedReimbursementData = reimbursementData.filter(r => r.reimbStatusId.reimbStatus == "submitted");
    populateDashboardTable(submittedReimbursementData);
}

function filterPending(){
    let submittedReimbursementData = reimbursementData.filter(r => r.reimbStatusId.reimbStatus == "pending");
    populateDashboardTable(submittedReimbursementData);
}

function filterApproved(){
    let submittedReimbursementData = reimbursementData.filter(r => r.reimbStatusId.reimbStatus == "approved");
    populateDashboardTable(submittedReimbursementData);
}

function filterPaid(){
    let submittedReimbursementData = reimbursementData.filter(r => r.reimbStatusId.reimbStatus == "paid");
    populateDashboardTable(submittedReimbursementData);
}

function filterDenied(){
    let submittedReimbursementData = reimbursementData.filter(r => r.reimbStatusId.reimbStatus == "denied");
    populateDashboardTable(submittedReimbursementData);
}

function populateDashboardTable(data){
    let tBody = document.querySelector("#dashboard_table_body");
    tBody.innerHTML = "";
    for (i in data){
        tr = document.createElement("tr");

        td1 = document.createElement("td");
        tdText1 = document.createTextNode(data[i].reimbId);
        td1.appendChild(tdText1);
        tr.appendChild(td1);

        td2 = document.createElement("td");
        tdText2 = document.createTextNode(data[i].reimbSubmittedString);
        td2.appendChild(tdText2);
        tr.appendChild(td2);

        td3 = document.createElement("td");
        tdText3 = document.createTextNode(data[i].reimbAuthor.firstName+" "+data[i].reimbAuthor.lastName);
        td3.appendChild(tdText3);
        tr.appendChild(td3);

        td4 = document.createElement("td");
        tdText4 = document.createTextNode(data[i].reimbAmount);
        td4.appendChild(tdText4);
        tr.appendChild(td4);

        td5 = document.createElement("td");
        tdText5 = document.createTextNode(data[i].reimbTypeId.reimbType);
        td5.appendChild(tdText5);
        tr.appendChild(td5);

        // ACTIONS DROPDOWN MENU
        if(data[i].reimbStatusId.reimbStatusId < 3){
            td6 = document.createElement("td");
            tdActionDiv = document.createElement("div");
            tdActionDiv.classList.add("dropdown");

            tdActionBtn = document.createElement("button");
            tdActionBtn.classList.add("btn");
            tdActionBtn.classList.add("btn-primary");
            // tdActionBtn.classList.add("dropdown-toggle");
            tdActionBtn.type = "button";
            tdActionBtn.setAttribute("data-bs-toggle", "dropdown");
            tdActionBtn.setAttribute("id", "dropdownAction"+i);
            tdActionBtn.setAttribute("aria-expanded", "false");
            // <i class="bi bi-journal-check"></i>
            tdActionBtnSymbol = document.createElement("i");
            tdActionBtnSymbol.classList.add("bi");
            tdActionBtnSymbol.classList.add("bi-journal-check");
            tdActionBtn.appendChild(tdActionBtnSymbol);
            tdActionDiv.appendChild(tdActionBtn);

            tdActionUl = document.createElement("ul");
            tdActionUl.classList.add("dropdown-menu");
            tdActionUl.setAttribute("aria-labelledby", "dropdownAction"+i);

            tdActionLi1 = document.createElement("li");
            tdActionA1 = document.createElement("a");
            tdActionA1.classList.add("dropdown-item");
            //TODO: add ID for event listener, or OnClick to perform action
            tdActionA1Text = document.createTextNode("Pending");
            tdActionA1.appendChild(tdActionA1Text);
            tdActionA1.classList.add("disabled");
            tdActionLi1.appendChild(tdActionA1);
            tdActionUl.appendChild(tdActionLi1);

            tdActionLi2 = document.createElement("li");
            tdActionLi2.addEventListener("click", showApprovedModal);
            tdActionA2 = document.createElement("a");
            tdActionA2.id = "a_approve_"+data[i].reimbId;
            tdActionA2.classList.add("dropdown-item");
            tdActionA2Text = document.createTextNode("Approve");
            tdActionA2.appendChild(tdActionA2Text);
            tdActionLi2.appendChild(tdActionA2);
            tdActionUl.appendChild(tdActionLi2);

            tdActionLi3 = document.createElement("li");
            tdActionA3 = document.createElement("a");
            tdActionA3.classList.add("dropdown-item");
            //TODO: add ID for event listener, or OnClick to perform action
            tdActionA3Text = document.createTextNode("Pay");
            tdActionA3.appendChild(tdActionA3Text);
            tdActionA3.classList.add("disabled");
            tdActionLi3.appendChild(tdActionA3);
            tdActionUl.appendChild(tdActionLi3);

            tdActionLi4 = document.createElement("li");
            tdActionA4 = document.createElement("a");
            tdActionA4.classList.add("dropdown-item");
            //TODO: add ID for event listener, or OnClick to perform action
            tdActionA4Text = document.createTextNode("Deny");
            tdActionA4.appendChild(tdActionA4Text);
            tdActionLi4.appendChild(tdActionA4);
            tdActionUl.appendChild(tdActionLi4);

            tdActionDiv.appendChild(tdActionUl);
            td6.appendChild(tdActionDiv);
            tr.appendChild(td6);
        } else {
            td6 = document.createElement("td");
            tr.appendChild(td6);
        }
        
        // ACTIONS DROPDOWN MENU


        td7 = document.createElement("td");
        if(data[i].reimbResolvedString===null){
            tdText7 = document.createTextNode("----/--/--");
        } else {
            tdText7 = document.createTextNode(data[i].reimbResolvedString);
        }
        td7.appendChild(tdText7);
        tr.appendChild(td7);

        td8 = document.createElement("td");
        if(data[i].reimbResolver===null){
            tdText8 = document.createTextNode("**No Resolver");
        } else {
            tdText8 = document.createTextNode(data[i].reimbResolver.firstName+" "+data[i].reimbResolver.lastName);
        }
        td8.appendChild(tdText8);
        tr.appendChild(td8);

        td9 = document.createElement("td");
        tdText9 = document.createTextNode(data[i].reimbStatusId.reimbStatus);
        td9.appendChild(tdText9);
        if(data[i].reimbStatusId.reimbStatusId==4){
            td9.style.color = "green";
        } else if(data[i].reimbStatusId.reimbStatusId==5){
            td9.style.color = "red";
        } else if(data[i].reimbStatusId.reimbStatusId==3){
            td9.style.color = "blue";
        } else {
            td9.style.backgroundColor = "lightgoldenrodyellow";
        }
        tr.appendChild(td9);

        tBody.appendChild(tr);
    }
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

function showApprovedModal(e) {
    let currentReimbursementId = (e.target.attributes.id.value).split('_')[2];

    $('#modal_approve').modal('show');
    let currentReimbursement = reimbursementData.filter(r => { return r.reimbId == currentReimbursementId })[0];
    // let currentReimbursement = currentReimbursementList[0];

    let modalApproveId = document.querySelector("#approve_id");
    modalApproveId.value = currentReimbursement.reimbId;

    let modalApproveAuthor = document.querySelector("#approve_author");
    modalApproveAuthor.value = (currentReimbursement.reimbAuthor.firstName)+" "+(currentReimbursement.reimbAuthor.lastName);

    let modalApproveAmount = document.querySelector("#approve_amount");
    modalApproveAmount.value = currentReimbursement.reimbAmount;

    let modalApproveType = document.querySelector("#approve_type");
    modalApproveType.value = currentReimbursement.reimbTypeId.reimbType;

}

function ApproveReimbursement() {
    let reimbursementId = document.querySelector('#approve_id').value;

    let data = {
        reimbId: reimbursementId,
    };

    fetch('http://localhost:7000/approve_reimbursement', {
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
        let divElement = document.querySelector('#approve-result-display');
        divElement.style.display="block";
        divElement.innerHTML = '';

        let pElement = document.createElement('p');
        pElement.style.color = 'green';
        pElement.innerHTML = 'Reimbursement successfully approved.';

        divElement.appendChild(pElement);
    })
}

function displayBadParameterFail() {
    let divElement = document.querySelector('#approve-result-display');
    divElement.style.display="block";
    divElement.innerHTML = '';

    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.innerHTML = 'Invalid input found! Reimbursement add failed.';

    divElement.appendChild(pElement);
}

function displayRetrieveFail() {
    let divElement = document.querySelector('#approve-result-display');
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