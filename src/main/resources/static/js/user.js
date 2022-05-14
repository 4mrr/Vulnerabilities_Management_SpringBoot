$('document').ready(function()
{
    $('#editButtom').on('click',function(event)
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


    $('#deleteButtom').on('click',function(event)
    {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButtom').attr('href',href);
        $('#deleteModal').modal('show');

    });

});