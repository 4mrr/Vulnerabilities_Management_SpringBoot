$('document').ready(function()
{
    $('.table #editButtom').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href,function(user,status)
        {
            $('#editFirstName').val(user.firstName);
            $('#editLastName').val(user.lastName);
            $('#editUsername').val(user.username);
            $('#editSpeciality').val(user.speciality);
            $('#editAdress').val(user.adress);
            $('#editEmail').val(user.email);
            $('#editPassword').val(user.password);
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
            $.get(href, function(user, status){
            $('#detailsFirstName').val(user.firstName);
            $('#detailsLastName').val(user.lastName);
            $('#detailsUsername').val(user.username);
            $('#detailsSpeciality').val(user.speciality);
            $('#detailsAdress').val(user.adress);
            $('#detailsEmail').val(user.email);
            $('#detailsPassword').val(user.password);
        });
        $('#detailsModal').modal('show');
    });

});