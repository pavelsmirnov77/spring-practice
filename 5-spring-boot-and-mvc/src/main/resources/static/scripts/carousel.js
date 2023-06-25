const carousel = document.querySelector('.carousel');
const carouselInner = carousel.querySelector('.carousel-inner');
const carouselItems = Array.from(carouselInner.children);
const prevButton = carousel.querySelector('.carousel-prev');
const nextButton = carousel.querySelector('.carousel-next');

prevButton.addEventListener('click', () => {
    const currentItem = carousel.querySelector('.carousel-item.active');
    const prevItem = currentItem.previousElementSibling;
    if (prevItem) {
        currentItem.classList.remove('active');
        prevItem.classList.add('active');
    }
});

nextButton.addEventListener('click', () => {
    const currentItem = carousel.querySelector('.carousel-item.active');
    const nextItem = currentItem.nextElementSibling;
    if (nextItem) {
        currentItem.classList.remove('active');
        nextItem.classList.add('active');
    }
});
