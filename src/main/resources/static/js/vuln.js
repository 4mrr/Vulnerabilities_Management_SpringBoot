$('document').ready(function()
{

    $('#deleteButtom-v').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal-v').modal('show');

    });

});