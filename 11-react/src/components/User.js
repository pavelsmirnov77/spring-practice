import React, {useState} from 'react'
import {Registration} from "./Registration"

export const users = [
    {
        id: 1,
        name: 'Павел',
        login: 'pavel123',
        password: '123',
        email: 'pavel@yandex.ru',
        balance: 2000
    },
    {
        id: 2,
        name: 'Валентин',
        login: 'valera123',
        password: '123',
        email: 'valera@yandex.ru',
        balance: 5000
    },
    {
        id: 3,
        name: 'Светлана',
        login: 'sveta123',
        password: '123',
        email: 'sveta@yandex.ru',
        balance: 10000
    }
]


export const User = ({currentUser, onUserChange}) => {
    const [showRegistration, setShowRegistration] = useState(false)

    const handleUserChange = (event) => {
        const selectedUserId = parseInt(event.target.value)
        const selectedUser = users.find((user) => user.id === selectedUserId);
        onUserChange(selectedUser);
    }

    const handleOpenRegistration = () => {
        setShowRegistration(true)
    }

    const handleRegister = (newUser) => {
        users.push(newUser);
        setShowRegistration(false)
        onUserChange(newUser)
    }

    return (
        <div>
            <h2>Информация о пользователе</h2>
            <select
                className={"select-field"}
                value={currentUser ? currentUser.id : ''}
                onChange={handleUserChange}>
                <option>Выбрать пользователя</option>
                {users.map((user) => (
                    <option key={user.id} value={user.id}>
                        {user.login}
                    </option>
                ))}
            </select>
            {!currentUser && (
                <button
                    className={"register-button"}
                    onClick={handleOpenRegistration}>
                    Зарегистрироваться
                </button>
            )}
            {currentUser && (
                <div className={"user-card"}>
                    <h4>Здравствуйте, {currentUser.name}!</h4>
                    <p>Псевдоним: {currentUser.login}</p>
                    <p>Email: {currentUser.email}</p>
                </div>
            )}
            {showRegistration && <Registration onRegister={handleRegister} currentUser={currentUser}/>}
        </div>
    )
}

