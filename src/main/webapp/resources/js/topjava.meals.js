var ctx;
var updateSpecificTable;

function clearFilter() {
    $('#filter')[0].reset();
    $.get(ctx.ajaxUrl, updateTableByData);
}

$(function () {
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "desc"
                ]
            ]
        })
    };
    updateSpecificTable = function () {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $('#filter').serialize()
        }).done(updateTableByData);
    }
    makeEditable();
});