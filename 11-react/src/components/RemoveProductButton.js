import React from 'react'

export const RemoveProductButton = ({ onRemove }) => {
    const handleRemoveClick = () => {
        onRemove()
    };

    return (
        <button
            className={"button-delete"}
            onClick={handleRemoveClick}>
            Удалить
        </button>
    )
}
