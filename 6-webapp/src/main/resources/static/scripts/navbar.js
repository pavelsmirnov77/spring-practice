document.querySelector('.navbar-toggle').addEventListener('click', function() {
    this.classList.toggle('open');

    document.querySelector('.navbar-menu').classList.toggle('open');
});

document.querySelectorAll('.navbar-menu li a').forEach(function(link) {
    link.addEventListener('click', function(e) {
        e.preventDefault();

        let target = document.querySelector(this.getAttribute('href'));
        let offsetTop = target.offsetTop;

        window.scrollTo({
            top: offsetTop,
            behavior: 'smooth'
        });
    });
});
