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
    createChart();
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
            tdActionLi4.addEventListener("click", showDeniedModal);
            tdActionA4 = document.createElement("a");
            tdActionA4.id = "a_deny_"+data[i].reimbId;
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

        td10 = document.createElement("td");
        if(data[i].reimbStatusId.reimbStatusId<3){

            tdActionBtn = document.createElement("button");
            tdActionBtn.classList.add("btn");
            tdActionBtn.type = "button";
            tdActionBtn.setAttribute("id", "reimb_Receipt_"+data[i].reimbId);
            tdActionBtn.addEventListener("click", showReceiptModal);

            tdActionBtnSymbol = document.createElement("i");
            tdActionBtnSymbol.classList.add("bi");
            tdActionBtnSymbol.setAttribute("id", "reimbReceipt_symbol_"+data[i].reimbId);

            if(data[i].reimbReceipt != null){
                tdActionBtn.classList.add("btn-info");
                tdActionBtnSymbol.classList.add("bi-image");
                tdActionBtn.appendChild(tdActionBtnSymbol);
                td10.appendChild(tdActionBtn);
            } else {
                tdActionBtn.classList.add("btn-warning");
                tdActionBtn.disabled = true;
                tdActionBtnSymbol.classList.add("bi-exclamation-triangle-fill");
                tdActionBtn.appendChild(tdActionBtnSymbol);
                td10.appendChild(tdActionBtn);
            }
        }
            else {
            tdText10 = document.createTextNode("");
            td10.appendChild(tdText10);
        }
        tr.appendChild(td10);

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
    let approveBtn = document.querySelector("#approve_ticket_submit");
    approveBtn.style.display = "block";
    let denyBtn = document.querySelector("#deny_ticket_submit");
    denyBtn.style.display = "none";


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

function showDeniedModal(e) {
    let currentReimbursementId = (e.target.attributes.id.value).split('_')[2];

    $('#modal_approve').modal('show');
    let approveBtn = document.querySelector("#approve_ticket_submit");
    approveBtn.style.display = "none";
    let denyBtn = document.querySelector("#deny_ticket_submit");
    denyBtn.style.display = "block";

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

function showReceiptModal(e) {
    let currentReimbursementId = (e.target.attributes.id.value).split('_')[2];

    $('#display_modal').modal('show');

    let imgContainer = document.querySelector("#display-img-fill");
    imgContainer.innerHTML = "";
    let imgElement = document.createElement("img");
    imgElement.src = "http://localhost:7000/reimbursement_receipt/"+currentReimbursementId;
    imgContainer.appendChild(imgElement);

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
        renderLastReimbursementData()
    })
}

function DenyReimbursement() {
    let reimbursementId = document.querySelector('#approve_id').value;

    let data = {
        reimbId: reimbursementId,
    };

    fetch('http://localhost:7000/deny_reimbursement', {
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
        pElement.innerHTML = 'Reimbursement successfully denied.';

        divElement.appendChild(pElement);
        renderLastReimbursementData()
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

function createChart(){

    let margin = {top: 20, right: 20, bottom: 70, left: 40},
        width = 700 - margin.left - margin.right,
        height = 600 - margin.top - margin.bottom;

    // let parseDate = d3.isoParse;
    // let submittedArray;
    // let amountArray;
    let unsortedData= [];
    let dataObj = {};
    let submitArray = [];
    for(i in reimbursementData){
        let dataObj = {};
        // submittedArray.add(parseDate(reimbursementData[i].reimbSubmittedString));
        // amountArray.add(reimbursementData[i].reimbAmount);
        dataObj.submit = reimbursementData[i].reimbSubmittedString;
        // dataObj.submit = Date.parse(reimbursementData[i].reimbSubmittedString);
        submitArray = Date.parse(reimbursementData[i].reimbSubmittedString);
        // console.log("String "+reimbursementData[i].reimbSubmittedString);
        // console.log("Date "+dataObj.submit);
        dataObj.amount = reimbursementData[i].reimbAmount;

        unsortedData.push(dataObj);
    }

    let uncleanData = unsortedData.reverse();
    console.log(uncleanData);
    
    let dates = uncleanData.map(function(x) { return new Date(x.submit); })

    var latest = new Date(Math.max.apply(null,dates));
    var earliest = new Date(Math.min.apply(null,dates));

    function monthDiff(d1, d2) {
        var months;
        months = (d2.getFullYear() - d1.getFullYear()) * 12;
        months -= d1.getMonth();
        months += d2.getMonth();
        return months <= 0 ? 0 : months;
    }

    let dataRows = monthDiff(earliest,latest);

    let data = [];
    let month = parseInt(uncleanData[0].submit.split('/')[1]);
    let year = parseInt(uncleanData[0].submit.split('/')[0]);
    for(i = 0; i<=dataRows; i++,month++){
        let dataObj = {}
        dataObj.amount = 0;
        for(j in uncleanData){
            if(parseInt(uncleanData[j].submit.split('/')[1]) == month && (uncleanData[j].submit.split('/')[0]) == year){
                dataObj.submit = (uncleanData[j].submit.split('/')[0])+"-"+(uncleanData[j].submit.split('/')[0]);
                dataObj.amount += uncleanData[j].amount;
            }
        }
        data.push(dataObj);
        if(month==12){
            month=0;
            year++;
        }
    }

    // for(year = data[0].submit.getUTCFullYear(); year<=data[data.length-1].submit.getUTCFullYear(); year++){
    //     for(month = data[0].submit.getUTCMonth()+1){

    //     }
    // }

    // x = d3.scaleOrdinal()
    //     .domain(data.map(d => d.submit))
    //     .range([0, width]);
    
    // x = d3.scaleBand()
    x = d3.scaleOrdinal()
        .domain(data.map(function(d) { return Date.parse(d.date); }))
        .range([0, width]);

    y = d3.scaleLinear()
        .domain([0, d3.max(data, d => d.amount)+100])
        .range([height, 0]);

    // xFormat = "%Y-%b-%d";
    // parseTime = d3.timeParse("%Y/%m/%d");

    xAxis = d3.axisBottom()
        .scale(x)
        .tickFormat(d3.timeFormat("%Y-%b"));

    yAxis = d3.axisLeft()
        .scale(y)
        .ticks(10);

    let svgDiv = document.getElementById("svg-div");
    svgDiv.innerHTML = "";
    
    let svg = d3.select("#svg-div").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform",
                "translate(" + margin.left + "," + margin.top + ")");
    
    
    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis.ticks(null).tickSize(0))
        .append("text")
            .style("text-anchor", "middle")
            .text("Value")
            .attr("transform", "rotate(-90)" );

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis.ticks(null).tickSize(0))
        .append("text")
            .attr("y", 6)
            .style("text-anchor", "middle")
            .text("Value");
  
    svg.selectAll("bar")
        .data(data)
        .enter().append("rect")
            // if amount < certainAmount then color different
            // .style("fill", function(d){ return d.value < d.target ? '#EF5F67': '#3FC974'})
            .style("fill", "steelblue")
            .attr("x", function(d,i) { return i*(x(d.submit)/(data.length)); })
            // .attr("width", x.bandwidth()/data.length)
            .attr("width", width/(data.length*2))
            // .attr("width", 5)
            .attr("y", function(d) { return y(d.amount); })
            .attr("height", function(d) { return height - y(d.amount); });

    // svg.selectAll("lines")
    //     .data(data)
    //     .enter().append("line")
    //         .style("fill", 'none')
    //         .attr("x1", function(d) { return (x(d.submit) + x.bandwidth()); })
    //         .attr("x2", function(d) { return x(d.submit); })
    //         .attr("y1", function(d) { return y(d.amount); })
    //         .attr("y2", function(d) { return y(d.amount); })
    //         .style("stroke-dasharray", [2,2])
    //         .style("stroke", "#000000")
    //         .style("stroke-width", 2);

    // const svg = d3.create("svg").attr("viewBox", [0, 0, width, height]);
    
    // svg.append("g")
    //     .attr("fill", color)
    //     .selectAll("rect")
    //     .data(data)
    //     .join("rect")
    //     .attr("x", (d, i) => x(i))
    //     .attr("y", d => y(d.value))
    //     .attr("height", d => y(0) - y(d.value))
    //     .attr("width", x.bandwidth());
    
    // svg.append("g")
    //     .call(xAxis);
    
    // svg.append("g")
    //     .call(yAxis);
    
    // return svg.node();
    
}