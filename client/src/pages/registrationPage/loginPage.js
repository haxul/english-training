import React, {useState} from "react"
import "./loginPage.css"
import {baseUrl} from "../../utils/connection"

export function LoginPage() {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const [isError, setError] = useState(false)
    const tryAuth = async () => {
        setError(false)
        const headers = new Headers();
        headers.append("Content-Type", "application/json");

        const url =  baseUrl + "/users/login"
        const body = {
            "account": login,
            "password": password
        }
        const response = await fetch(url, {
            method: "POST",
            headers : headers,
            body: JSON.stringify(body),
            Host: baseUrl,
            Origin: "http://localhost:3000"
        })

        if (response.status === 200) {
            const data = await response.json()
            localStorage.setItem("token", data.token)
            localStorage.setItem("account", login)
            document.location.reload(true);
        } else setError(true)
    }

    return (
        <div className={"container"}>
            <div className={"row justify-content-center"}>
                <div className={"loginWrapper col-4 "}>
                    <p className={`headerLogin ${isError ? "isError"  : ""}` }>{ isError? "Incorrect Password or Username" : "Please, Login"}</p>
                    <p><input type="text" className={"inputLogin"} onChange={(e => setLogin(e.target.value))}/></p>
                    <p><input type="password" className={"inputLogin"} onChange={e => setPassword(e.target.value)}/></p>
                    <p>
                        <button onClick={tryAuth} className={"buttonLogin"}>login</button>
                    </p>
                </div>
            </div>
        </div>
    )
}