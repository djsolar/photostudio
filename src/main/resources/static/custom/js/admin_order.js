$(function () {
    init_SmartWizard();
    init_operation_handler();
});

function init_operation_handler() {
    $("#addOrder").click(function () {
        $(".right_col").load("/admin/addOrder");
    });
}

/* SMART WIZARD */

function init_SmartWizard() {

    if (typeof ($.fn.smartWizard) === 'undefined') {
        return;
    }
    console.log('init_SmartWizard');

    $('#wizard').smartWizard();

    $('#wizard_verticle').smartWizard({
        transitionEffect: 'slide'
    });

    $('.buttonNext').addClass('btn btn-success');
    $('.buttonPrevious').addClass('btn btn-primary');
    $('.buttonFinish').addClass('btn btn-default');

};