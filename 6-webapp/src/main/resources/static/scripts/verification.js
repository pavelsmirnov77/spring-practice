window.addEventListener('DOMContentLoaded', function() {
    let verificationOverlay = document.getElementById('verification-overlay');
    let messageOverlay = document.getElementById('message-overlay');
    let confirmButton = document.getElementById('confirm-button');
    let nonConfirmButton = document.getElementById('non-confirm-button');
    let verificationMessage = document.getElementById('message-box');

    confirmButton.addEventListener('click', function() {
        verificationOverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    });

    nonConfirmButton.addEventListener('click', function() {
        verificationMessage.style.display = 'block';
        messageOverlay.style.display = 'block';
    });

    verificationOverlay.addEventListener('wheel', function(event) {
        event.preventDefault();
    });

    verificationOverlay.addEventListener('touchmove', function(event) {
        event.preventDefault();
    });

    document.body.style.overflow = 'hidden';
});
