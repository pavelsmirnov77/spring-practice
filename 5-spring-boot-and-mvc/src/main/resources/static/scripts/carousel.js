// carousel.js
const carousel = document.querySelector('.carousel');
const carouselInner = carousel.querySelector('.carousel-inner');
const carouselItems = Array.from(carouselInner.children);
const prevButton = carousel.querySelector('.carousel-prev');
const nextButton = carousel.querySelector('.carousel-next');

// Количество видов пива в карусели
const itemCount = carouselItems.length;

// Ширина каждого элемента (вид пива)
const itemWidth = carouselItems[0].offsetWidth;

// Позиция текущего элемента
let currentPosition = 0;

// Обработчик нажатия на кнопку "Предыдущий"
prevButton.addEventListener('click', () => {
    currentPosition = (currentPosition - 1 + itemCount) % itemCount;
    updateCarouselPosition();
});

// Обработчик нажатия на кнопку "Следующий"
nextButton.addEventListener('click', () => {
    currentPosition = (currentPosition + 1) % itemCount;
    updateCarouselPosition();
});

// Обновление позиции карусели
function updateCarouselPosition() {
    const translateX = -currentPosition * itemWidth;
    carouselInner.style.transform = `translateX(${translateX}px)`;
}
