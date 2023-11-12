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

to("effort-console");

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