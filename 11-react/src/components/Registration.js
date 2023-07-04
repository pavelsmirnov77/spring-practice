import React, {useState} from 'react'
import {users} from "./User"

export const Registration = ({onRegister, currentUser}) => {
    const [name, setName] = useState('')
    const [login, setLogin] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const handleRegister = () => {
        const newUser = {
            id: users.length + 1,
            name,
            login,
            password,
            email,
            balance: 10000,
        }
        onRegister(newUser)
        setName('')
        setLogin('')
        setEmail('')
        setPassword('')
    }
    if (currentUser) {
        return null;
    }
    return (
        <div>
            <h2>Регистрация нового пользователя</h2>
            <div>
                <input
                    className={"input-text"}
                    type="text"
                    placeholder="Имя"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <div>
                <input
                    className={"input-text"}
                    type="text"
                    placeholder="Логин"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                />
            </div>
            <div>
                <input
                    className={"input-text"}
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <div>
                <input
                    className={"input-text"}
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <button
                className={"register-button"}
                onClick={handleRegister}>
                Зарегистрироваться
            </button>
        </div>
    )
}
