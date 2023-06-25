window.addEventListener('DOMContentLoaded', function() {
    var ageVerificationOverlay = document.getElementById('age-verification-overlay');
    var ageConfirmButton = document.getElementById('age-confirm-button');

    ageConfirmButton.addEventListener('click', function() {
        ageVerificationOverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    });

    ageVerificationOverlay.addEventListener('wheel', function(event) {
        event.preventDefault();
    });

    ageVerificationOverlay.addEventListener('touchmove', function(event) {
        event.preventDefault();
    });

    document.body.style.overflow = 'hidden';
});
