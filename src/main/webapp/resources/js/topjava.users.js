var ctx;
var updateSpecificTable;

function enable(checkBox, id) {
    let isOn = checkBox[0].checked;
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
        data: "isEnabled=" + isOn,
    }).done(function () {
        checkBox.parents("tr").attr("data-userEnabled", isOn)
        successNoty(isOn ? "Enabled" : "Disabled");
    }).fail(function () {
            checkBox[0].checked = !isOn;
        });
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
    updateSpecificTable = function () {
        $.get(ctx.ajaxUrl, updateTableByData);
    }
    makeEditable();
});