$('document').ready(function()
{

    $('#deleteButtom').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal').modal('show');
    });
});