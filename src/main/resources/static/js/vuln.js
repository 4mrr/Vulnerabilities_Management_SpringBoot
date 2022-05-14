$('document').ready(function()
{
    $('#editButtom-v').on('click',function(event)
    {
        event.preventDefault();

        var href = $(this).attr('href');
        $.get(href,function(vulnn,status)
        {
            $('#editAuthor').val(vulnn.author);
            $('#editTitle').val(vulnn.title);
            $('#editverify').val(vulnn.verify);
            $('#editDate').val(vulnn.date);
            $('#editPlatform').val(vulnn.platform);
            $('#editDescription').val(vulnn.description);
        });
        $('#editModal-v').modal('show');
    })


    $('#deleteButtom-v').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal-v').modal('show');

    });

});