import React, {useState} from 'react'

export const SearchBar = ({onSearch}) => {
    const [searchTerm, setSearchTerm] = useState('')

    const handleChange = (event) => {
        setSearchTerm(event.target.value)
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        onSearch(searchTerm)
    }

    return (
        <form onSubmit={handleSubmit}>
            <input
                className={"input-text"}
                type="text"
                value={searchTerm}
                onChange={handleChange}
                placeholder="Поиск по названию"
            />
            <button
                className={"button-small"}
                type="submit">
                Поиск
            </button>
        </form>
    )
}
