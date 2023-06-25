document.querySelector('.navbar-toggle').addEventListener('click', function() {
    this.classList.toggle('open');

    document.querySelector('.navbar-menu').classList.toggle('open');
});

document.querySelectorAll('.navbar-menu li a').forEach(function(link) {
    link.addEventListener('click', function(e) {
        e.preventDefault();

        var target = document.querySelector(this.getAttribute('href'));
        var offsetTop = target.offsetTop;

        window.scrollTo({
            top: offsetTop,
            behavior: 'smooth'
        });
    });
});
