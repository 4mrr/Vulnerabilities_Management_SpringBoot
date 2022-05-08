$('document').ready(function()
{
    $('#editButtom').on('click',function(event)
    {
        event.preventDefault();

        var href = $(this).attr('href');
        $.get(href,function(role,status)
        {
            $('#editId').val(role.id);
            $('#editDescription').val(role.description);
            $('#editDetail').val(role.detail);
        });
        $('#editModal').modal('show');
    })

    $('#deleteButtom').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal').modal('show');
    });

    $('#detailsButton').on('click',function(event)
    {
        event.preventDefault();

        var href= $(this).attr('href');

        $.get(href, function(role, status){
            $('#detailsId').val(role.id);
            $('#detailsDescription').val(role.description);
            $('#detailsDetail').val(vulnn.detail);
        });
        $('#detailsModal').modal('show');
    });
});