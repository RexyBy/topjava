var ctx;

function enable(checkBox, id) {
    let isOn = checkBox[0].checked;
    setOpacity(checkBox.parents("tr"), isOn ? 1 : 0.3)
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id,
        data: "isEnabled=" + isOn
    }).done(function () {
        updateTable();
        successNoty(isOn ? "Enabled" : "Disabled");
    });
}

function setOpacity(row, opacity) {
    row.css("opacity", opacity);
}

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    };
    makeEditable();
});