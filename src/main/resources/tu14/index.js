"use strict";

let {render, signal, component} = reef;

function PasswordField(placeholder) {


    let data = signal({password: "", visible: false});

    let events = {
        toggle() {
            data.visible = !data.visible;
        }
    }

    function template() {
        let {password, visible} = data;

        return `<div class="border-0 rounded-md p-0 m-0 bg-gray-100 flex flex-row items-stretch">
                    <input class="rounded-l-md border-0 bg-inherit hover:bg-gray-200" type="${visible ? "text" : "password"}">
                    <button class="text-2xl/6 rounded-r-md p-2 !outline-none hover:bg-gray-200">${visible ? "-" : "👁"}</button>
                </div>`;
    }

    return {data, template, events}
}

let field = PasswordField();

component("#main", field.template, field.events );