window.addEventListener('DOMContentLoaded', function() {
    var ageVerificationOverlay = document.getElementById('age-verification-overlay');
    var ageConfirmButton = document.getElementById('age-confirm-button');

    ageConfirmButton.addEventListener('click', function() {
        ageVerificationOverlay.style.display = 'none';
    });
});
