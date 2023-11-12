"use strict";

function PasswordField(props) {
    return {
        $template: "#passwordFieldTemplate",
        visible: false,
        id: props.name ?? "",
        type: "password",
        placeholder: props.placeholder,
        toggle() {
            this.visible = !this.visible;
            this.type = this.visible ? "text" : "password";
        }
    }
}

const pages = document.querySelector("#pages").children;

function to(_page) {
    for (const page of pages) {
        if (page.getAttribute("data-page") === _page)
            page.classList.toggle("hidden", false);
        else
            page.classList.toggle("hidden", true);
    }
}

to("login");

PetiteVue.createApp({PasswordField}).mount();

document.querySelector("#login-button").addEventListener("click", () => {
    const username = document.querySelector("#login-username").value;
    const password = document.querySelector("#login-password").value;


    const success = app.login(username, password);
    if (success == null) {
        app.log("Error occurred");
    } else if (!success) {
        app.log("Wrong credentials");
    } else {
        to("home");
    }
});

const effortTable = new Tabulator("#effort-table", {
    height: "100%",
    data: {},
    layout: "fitColumns",
    columns: [
        {title: "ID", field: "id"},
        {title: "Start Time", field: "start"},
        {title: "End Time", field: "end"},
        {title: "Life Cycle", field: "lifeCycle"},
        {title: "Effort Category", field: "effortCategory"},
        {title: "Deliverable", field: "deliverable"},
        {title: "Project", field: "project"},
    ]
});

const defectTable = new Tabulator("#defect-table", {
    height: "100%",
    data: {},
    layout: "fitColumns",
    columns: [
        {title: "ID", field: "id"},
        {title: "Name", field: "name"},
        {title: "Status", field: "status"},
        {title: "Fix", field: "fix"},
        {title: "Life Cycle Included", field: "lifeCycleIncluded"},
        {title: "Life Cycle Excluded", field: "lifeCycleExcluded"},
        {title: "Defect Category", field: "defectCategory"},
        {title: "Project", field: "project"},
    ]
});

const planningPokerNumbers = [0, 1, 2, 3, 5, 8, 13, 20, 40, 100];