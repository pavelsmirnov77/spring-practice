import React from 'react'

export const Payment = ({totalAmount, onPayment}) => {
    const handlePayment = () => {
        onPayment(totalAmount)
    }

    return (
        <div>
            <h2>Оплата</h2>
            <button
                className={"payment-button"}
                onClick={handlePayment}>
                Оплатить
            </button>
        </div>
    )
}
