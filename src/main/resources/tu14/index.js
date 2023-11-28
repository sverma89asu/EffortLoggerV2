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

const pages = document.querySelector("#pages");

function to(_page) {
    for (const page of pages.children) {
        if (page.getAttribute("data-page") === _page) page.classList.toggle("hidden", false); else page.classList.toggle("hidden", true);
    }

    pages.dispatchEvent(new CustomEvent("routing:" + _page));
}

function makeRequest(table, method = "GET") {
    return fetch("https://cse360.flerp.dev/tables/" + table, {
        method, headers: {
            Authorization: "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5"
        }
    });
}

function toast(string, type) {
    let col;
    let textCol;

    switch (type) {
        case "success":
            col = "#6ee7b7";
            textCol = "#111111";
            break;
        case "error":
            col = "#fca5a5";
            textCol = "#111111";
            break;
        case "warn":
            col = "#fdba74";
            textCol = "#111111";
            break;
        default:
            col = "#525252";
            textCol = "#eeeeee";
            break;
    }

    Toastify({
        text: string,
        duration: 3000,
        gravity: "bottom",
        position: "left",
        stopOnFocus: "true",
        style: {
            background: col, color: textCol
        }
    }).showToast();
}

to("login");

PetiteVue.createApp({PasswordField}).mount();

document.querySelector("#login-button").addEventListener("click", () => {
    const username = document.querySelector("#login-username").value;
    const password = document.querySelector("#login-password").value;


    const success = app.login(username, password);
    if (success == null) {
        toast("Unknown Error", "error");
    } else if (!success) {
        toast("Wrong Credentials", "warn");
    } else {
        toast("Logged in as " + username, "info");
        to("home");
    }
});

const projects = [];
const lifecycles = [];
const deliverables = [];
const defectCategories = [];
const effortCategories = [];


makeRequest("project").then(async (req) => {
    if (!req.ok) {
        return;
    }

    const json = await req.json();

    function makeOption(val) {
        return new Option(val.name, val.id);
    }

    const defectSelect = document.querySelector("#defect-select-project");
    const effortSelect = document.querySelector("#effort-select-project");
    const storySelect = document.querySelector("#story-select-project");

    for (const opt of json) {
        defectSelect.appendChild(makeOption(opt));
        effortSelect.appendChild(makeOption(opt));
        storySelect.appendChild(makeOption(opt));
        projects.push(opt);
    }
});

makeRequest("lifeCycle").then(async (req) => {
    if (!req.ok) {
        return;
    }

    const json = await req.json();

    function makeOption(val) {
        return new Option(val.name, val.id);
    }

    const defectSelect = document.querySelector("#defect-select-lifecycle");
    const effortSelect = document.querySelector("#effort-select-lifecycle");

    for (const opt of json) {
        defectSelect.appendChild(makeOption(opt));
        effortSelect.appendChild(makeOption(opt));
        lifecycles.push(opt);
    }
});

makeRequest("deliverable").then(async (req) => {
    if (!req.ok) {
        return;
    }

    const json = await req.json();

    function makeOption(val) {
        return new Option(val.name, val.id);
    }

    const effortSelect = document.querySelector("#effort-select-deliverable");

    for (const opt of json) {
        effortSelect.appendChild(makeOption(opt));
        deliverables.push(opt);
    }
});

makeRequest("defectCategory").then(async (req) => {
    if (!req.ok) {
        return;
    }

    const json = await req.json();

    function makeOption(val) {
        return new Option(val.name, val.id);
    }

    const effortSelect = document.querySelector("#defect-select-category");

    for (const opt of json) {
        effortSelect.appendChild(makeOption(opt));
        effortCategories.push(opt);
    }
});

makeRequest("effortCategory").then(async (req) => {
    if (!req.ok) {
        return;
    }

    const json = await req.json();

    function makeOption(val) {
        return new Option(val.name, val.id);
    }

    const effortSelect = document.querySelector("#effort-select-category");

    for (const opt of json) {
        effortSelect.appendChild(makeOption(opt));
        defectCategories.push(opt);
    }
});


const effortTable = new Tabulator("#effort-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Start Time", field: "start", formatter: "datetime", formatterParams: {
            inputFormat: "iso", outputFormat: "ff", invalidPlaceholder: "(invalid date)",
        }
    }, {
        title: "End Time", field: "end", formatter: "datetime", formatterParams: {
            inputFormat: "iso", outputFormat: "ff", invalidPlaceholder: "(invalid date)"
        }
    }, {
        title: "Life Cycle",
        field: "lifeCycle",
        editor: "list",
        editorParams: {valuesLookup: () => lifecycles.map((a) => ({label: a.name, value: a.id}))},
        formatter: (cell) => lifecycles.filter((a) => a.id === cell.getValue())[0].name
    }, {
        title: "Effort Category", field: "effortCategory", editor: "list", editorParams: {
            valuesLookup: () => effortCategories.map((a) => ({
                label: a.name, value: a.id
            }))
        }, formatter: (cell) => effortCategories.filter((a) => a.id === cell.getValue())[0].name
    }, {
        title: "Deliverable",
        field: "deliverable",
        editor: "list",
        editorParams: {valuesLookup: () => deliverables.map((a) => ({label: a.name, value: a.id}))},
        formatter: (cell) => deliverables.filter((a) => a.id === cell.getValue())[0].name
    }, {
        title: "Project",
        field: "project",
        editor: "list",
        editorParams: {valuesLookup: () => projects.map((a) => ({label: a.name, value: a.id}))},
        formatter: (cell) => projects.filter((a) => a.id === cell.getValue())[0].name
    }, {
        formatter: "buttonCross",
        headerSort: false,
        width: 40,
        align: "center",
        cellClick: (e, cell) => {
            cell.getRow().delete();
            if (service_EffortLog.deleteLog(cell.getRow().getData().id)) {
                toast("Deleted log", "info");
            } else {
                toast("Unknown Error", "error");
            }
        }
    },]
});

pages.addEventListener("routing:effort", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("effortLog")).json();
    effortTable.setData(json);
});

const defectTable = new Tabulator("#defect-table", {
    data: {},
    layout: "fitColumns",
    columns: [{title: "ID", field: "id"}, {title: "Name", field: "name"}, {
        title: "Description", field: "description", editor: "textarea", formatter: "textarea"
    }, {
        title: "Status",
        field: "status",
        editor: "list",
        editorParams: {values: [{label: "Open", value: false}, {label: "Closed", value: true}]},
        formatter: (cell) => cell.getValue() ? "Closed" : "Open",
    }, {title: "Fix", field: "fix", editor: "textarea", formatter: "textarea"}, {
        title: "Life Cycle Included",
        field: "lifeCycleIncluded",
        editor: "list",
        editorParams: {valuesLookup: () => lifecycles.map((a) => ({label: a.name, value: a.id}))},
        formatter: (cell) => lifecycles.filter((a) => a.id === cell.getValue())[0].name
    }, {
        title: "Life Cycle Excluded", field: "lifeCycleExcluded", editor: "list", editorParams: {
            clearable: true,
            valuesLookup: () => lifecycles.map((a) => ({label: a.name, value: a.id}))
        }, formatter: (cell) => lifecycles.filter((a) => a.id === cell.getValue())[0]?.name ?? ""
    }, {
        title: "Defect Category", field: "defectCategory", editor: "list", editorParams: {
            valuesLookup: () => defectCategories.map(a => ({
                label: a.name, value: a.id
            }))
        }, formatter: (cell) => defectCategories.filter((a) => a.id === cell.getValue())[0].name
    }, {
        title: "Project",
        field: "project",
        editor: "list",
        editorParams: {valuesLookup: () => projects.map((a) => ({label: a.name, value: a.id}))},
        formatter: (cell) => projects.filter((a) => a.id === cell.getValue())[0].name
    }, {
        formatter: "buttonCross",
        headerSort: false,
        width: 40,
        align: "center",
        cellClick: (e, cell) => {
            cell.getRow().delete();
            if (service_DefectLog.deleteLog(cell.getRow().getData().id)) {
                toast("Deleted log", "info");
            } else {
                toast("Unknown Error", "error");
            }
        }
    },]
});

pages.addEventListener("routing:defect", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("defectlog")).json();
    defectTable.setData(json);
})

const projectTable = new Tabulator("#project-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:project", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("project")).json();
    projectTable.setData(json);
});

const lifeCycleTable = new Tabulator("#lifecycle-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:lifecycle", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("lifecycle")).json();
    lifeCycleTable.setData(json);
});

const userStoriesTable = new Tabulator("#user-stories-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:user-stories", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("userstory")).json();
    userStoriesTable.setData(json);
});

const deliverablesTable = new Tabulator("#deliverables-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:deliverables", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("deliverable")).json();
    deliverablesTable.setData(json);
});

const effortCategoriesTable = new Tabulator("#effort-categories-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:effort-categories", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("effortcategory")).json();
    effortCategoriesTable.setData(json);
});

const defectCategoriesTable = new Tabulator("#defect-categories-table", {
    data: {}, layout: "fitColumns", columns: [{title: "ID", field: "id"}, {
        title: "Name", field: "name", editor: "input"
    }, {title: "Description", field: 'description', editor: "textarea", formatter: "textarea"}]
});

pages.addEventListener("routing:defect-categories", async (event) => {
    event.stopImmediatePropagation();
    const json = await (await makeRequest("defectcategory")).json();
    defectCategoriesTable.setData(json);
});

function saveGeneric(tableName) {
    const table = {
        project: projectTable,
        userstory: userStoriesTable,
        deliverable: deliverablesTable,
        effortcategory: effortCategoriesTable,
        defectcategory: defectCategoriesTable,
        lifecycle: lifeCycleTable,
    }[tableName];

    let data = table.getEditedCells().map((cell) => cell.getRow().getData());

    if (data.length === 0) {
        toast("Nothing to save", "info");
        return;
    }

    for (let {id, updatedAt, createdAt, ...rest} of data) {
        fetch("https://cse360.flerp.dev/tables/" + tableName, {
            method: "POST", headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5",
                "X-Data-ID": id.toString(),
            }, body: JSON.stringify(rest)
        });
    }

    toast("Saved changes", "success");


}

const effortLogProjectSelect = document.querySelector("#effort-select-project");
const effortLogLifeCycleSelect = document.querySelector("#effort-select-lifecycle");
const effortLogDeliverableSelect = document.querySelector("#effort-select-deliverable");
const effortLogCategorySelect = document.querySelector("#effort-select-category");

function exportEffort() {
    const code = service_EffortLog.exportEffortLogs();

    switch (code) {
        case 1:
            toast("No logs to export", "info");
            break;
        case 2:
            break;
        case 3:
            toast("That file already exists", "warning");
            break;
        case 0:
            toast("Logs exported", "success");
            break;
        default:
            toast("Unknown Error", "error");
            break;

    }
}

function saveEffort() {
    const data = effortTable.getEditedCells().map((cell) => cell.getRow().getData());
    if (service_EffortLog.saveEffortLogs(data, data.length)) {
        toast("Saved Effort Logs", "success");
    } else {
        toast("Unknown Error", "error");
    }
}

function doEffortLog(element) {
    const project = parseInt(effortLogProjectSelect.value);
    const lifecycle = parseInt(effortLogLifeCycleSelect.value);
    const deliverable = parseInt(effortLogDeliverableSelect.value);
    const category = parseInt(effortLogCategorySelect.value);

    if (Number.isNaN(project + lifecycle + deliverable + category)) {
        toast("Invalid Selection", "error");
        return;
    }

    if (service_EffortLog.clockRunning) {
        if (service_EffortLog.completeEffortLog(lifecycle, category, deliverable, project)) {
            element.classList.toggle("button-sky", true);
            element.classList.toggle("button-emerald", false);
            element.textContent = "Start Activity";
            effortLogCategorySelect.disabled = false;
            effortLogDeliverableSelect.disabled = false;
            effortLogLifeCycleSelect.disabled = false;
            effortLogProjectSelect.disabled = false;
            toast("Completed Effort Log", "success")
        } else {
            toast("Unknown Error", "error");
        }
    } else {
        service_EffortLog.startClock();
        element.classList.toggle("button-sky", false);
        element.classList.toggle("button-emerald", true);
        element.textContent = "Activity Started";
        effortLogCategorySelect.disabled = true;
        effortLogDeliverableSelect.disabled = true;
        effortLogLifeCycleSelect.disabled = true;
        effortLogProjectSelect.disabled = true;

        toast("Started Clock", "info");
    }
}

const defectLogProjectSelect = document.querySelector("#defect-select-project");
const defectLogLifeCycleSelect = document.querySelector("#defect-select-lifecycle");
const defectLogNameInput = document.querySelector("#defect-select-name");
const defectLogCategorySelect = document.querySelector("#defect-select-category");
const defectLogDescriptionInput = document.querySelector("#defect-select-description");

function exportDefect() {
    const code = service_DefectLog.exportDefectLogs();

    switch (code) {
        case 1:
            toast("No logs to export", "info");
            break;
        case 2:
            break;
        case 3:
            toast("That file already exists", "warning");
            break;
        case 0:
            toast("Logs exported", "success");
            break;
        default:
            toast("Unknown Error", "error");
            break;

    }
}

function saveDefect() {
    const data = defectTable.getEditedCells().map((cell) => cell.getRow().getData());
    if (service_DefectLog.saveDefectLogs(data, data.length)) {
        toast("Saved Defect Logs", "success");
    } else {
        toast("Unknown Error", "error");
    }
}

function doDefectLog() {
    const project = parseInt(defectLogProjectSelect.value);
    const lifecycle = parseInt(defectLogLifeCycleSelect.value);
    const category = parseInt(defectLogCategorySelect.value);
    const name = defectLogNameInput.value;
    const description = defectLogDescriptionInput.value;

    if (Number.isNaN(project + lifecycle + category) || name.trim() === "") {
        toast("Invalid Selection", "error");
        return;
    }

    if (service_DefectLog.createDefectLog(name, description, project, lifecycle, category)) {
        toast("Created Defect Log", "success");
    } else {
        toast("Unknown Error", "error");
    }
}

const projectNameSelect = document.querySelector("#project-name");
const projectDescriptionInput = document.querySelector("#project-description");

async function createProject() {
    const name = projectNameSelect.value;
    const description = projectDescriptionInput.value;

    if (name.trim() === "") {
        toast("Invalid name", "error");
        return;
    }

    const data = await fetch("https://cse360.flerp.dev/tables/project", {
        method: "PUT", headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5",
        }, body: JSON.stringify({name, description})
    });

    if (data.ok) {
        toast("Created project with ID " + (await data.json()).id, "success");
    } else {
        toast("Unknown Error", "error");
    }

}

const storyNameSelect = document.querySelector("#story-name-select");
const storyDescriptionInput = document.querySelector("#story-description");
const storyProjectSelect = document.querySelector("#story-select-project");

async function createStory() {
    const name = storyNameSelect.value;
    const description = storyDescriptionInput.value;
    const project = parseInt(storyProjectSelect.value);

    if (name.trim() === "" || Number.isNaN(project)) {
        toast("Invalid input", "error");
        return;
    }

    const data = await fetch("https://cse360.flerp.dev/tables/userstory", {
        method: "PUT", headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5",
        }, body: JSON.stringify({name, description, project})
    });

    if (data.ok) {
        toast("Created project with ID " + (await data.json())[0].id, "success");
    } else {
        toast("Unknown Error", "error");
    }

}

async function loadStory() {
    const id = service_PlanningPoker.getStoryId();
    const story = (await (await makeRequest("userstory")).json()).filter(a => a.id === id)[0];

    if (!story) {
        toast("No known story with that ID", "error");
        to("poker-console");
    }

    document.querySelector("#story-name").textContent = story.name;
    document.querySelector("#story-content").textContent = story.description;
}

function joinSession() {
    if (service_PlanningPoker.joinSession(document.querySelector("#poker-session-code").value)) {
        to("poker-thing");
        toast("Joined planning poker session " + service_PlanningPoker.getSessionName());

        loadStory();
    } else {
        toast("Unknown Error", "error");
    }


}

function createSession() {

    const storyId = parseInt(document.querySelector("#poker-create-story-id").value);
    const name = document.querySelector("#poker-create-session-code").value;

    if (Number.isNaN(storyId) || name.trim() === "") {
        toast("Invalid input", "error");
        return;
    }

    if (service_PlanningPoker.createSession(name, storyId)) {
        to("poker-thing");
        toast("Create planning poker session " + service_PlanningPoker.getSessionName());

        loadStory();
    } else {
        toast("Unknown Error", "error");
    }
}

let pokerSubmitted = false;
let rounds = 0;

const pokerTable = new Tabulator("#poker-feedback-container", {
    data: {}, layout: "fitColumns", columns: [{title: "Story Points", field: "points"}, {title: "Note", field: "note"}]
})

async function progressPlanningPoker(element) {
    if (pokerSubmitted) {
        const data = (await (await makeRequest("planningpokerround")).json()).filter(item => item.round === rounds && item.pokerId === service_PlanningPoker.getPokerId());

        pokerTable.setData(data);
        rounds++;
        pokerSubmitted = false;

        element.classList.toggle("button-emerald", false);
        element.classList.toggle("button-sky", true);
        element.textContent = "Submit Points";
    } else {
        const note = document.querySelector("#poker-description").value;
        const points = parseInt(document.querySelector("#select-poker-points").value);

        if (Number.isNaN(points)) {
            toast("Invalid input", "error");
            return;
        }

        app.log(JSON.stringify(service_PlanningPoker.getPokerId()));

        fetch("https://cse360.flerp.dev/tables/planningpokerround", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5",
            },
            body: JSON.stringify({
                note,
                points,
                round: rounds,
                pokerId: service_PlanningPoker.getPokerId()
            })
        }).then(() => {
            toast("Submitted Points", "success");
        })

        pokerSubmitted = true;

        element.classList.toggle("button-sky", false);
        element.classList.toggle("button-emerald", true);
        element.textContent = "Receive Feedback";
    }
}