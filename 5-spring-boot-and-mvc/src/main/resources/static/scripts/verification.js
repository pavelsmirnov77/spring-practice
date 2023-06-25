window.addEventListener('DOMContentLoaded', function() {
    var ageVerificationOverlay = document.getElementById('age-verification-overlay');
    var ageConfirmButton = document.getElementById('age-confirm-button');

    ageConfirmButton.addEventListener('click', function() {
        ageVerificationOverlay.style.display = 'none';
        document.body.style.overflow = 'auto'; // Разрешить прокрутку страницы после подтверждения
    });

    ageVerificationOverlay.addEventListener('wheel', function(event) {
        event.preventDefault(); // Предотвращаем прокрутку при прокрутке колесом мыши над оверлеем
    });

    ageVerificationOverlay.addEventListener('touchmove', function(event) {
        event.preventDefault(); // Предотвращаем прокрутку при касании и прокрутке на оверлее на сенсорных устройствах
    });

    document.body.style.overflow = 'hidden'; // Запретить прокрутку страницы при отображении оверлея
});
