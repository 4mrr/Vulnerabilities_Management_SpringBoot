$('document').ready(function()
{
    $('.table #editButtom').on('click',function(event)
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

    $('.table #deleteButtom').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal').modal('show');
    });

    $('.table #detailsButton').on('click',function(event)
    {
        event.preventDefault();

        var href= $(this).attr('href');

        $.get(href, function(role, status){
            $('#detailsId').val(role.id);
            $('#detailsDescription').val(role.description);
            $('#detailsDetail').val(role.detail);
        });
        $('#detailsModal').modal('show');
    });
});