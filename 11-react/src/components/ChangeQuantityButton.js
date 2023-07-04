import React, {useState} from 'react'

export const ChangeQuantityButton = ({itemId, currentQuantity, onSaveQuantity, onCancel}) => {
    const [editedQuantity, setEditedQuantity] = useState(currentQuantity)

    const handleSave = () => {
        onSaveQuantity(itemId, editedQuantity)
    }

    const handleCancel = () => {
        onCancel()
    }

    return (
        <div>
            <input
                className="small-input"
                type="number"
                value={editedQuantity}
                onChange={(e) => setEditedQuantity(e.target.value)}
            />
            <button className="button-change" onClick={handleSave}>
                OK
            </button>
            <button className="button-change" onClick={handleCancel}>
                Отмена
            </button>
        </div>
    )
}
