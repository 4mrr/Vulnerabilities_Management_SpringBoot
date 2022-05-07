
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

        $('#detailsButton-v').on('click',function(event)
        {
            event.preventDefault();

            var href= $(this).attr('href');

            $.get(href, function(vulnn, status){
                $('#detailsidv').val(vulnn.id);
                $('#detailsAuthor').val(vulnn.author.username);
                $('#detailsTitle').val(vulnn.title);
                $('#detailsverify').val(vulnn.verify);
                $('#detailsDate').val(vulnn.date);
                $('#detailsPlatform').val(vulnn.platform);
                $('#detailsDescription').val(vulnn.description);
            });
            $('#detailsModal-v').modal('show');
        });

    });