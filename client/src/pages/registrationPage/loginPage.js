import React, {useState, useContext} from "react"
import "./loginPage.css"
import { baseUrl } from "../../utils/connection"

export function LoginPage() {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const [isError, setError] = useState(false)
    const tryAuth = () => {
        setError(false)
        const url = `${baseUrl}/login`
        const body = {
            "account": login,
            "password" : password
        }
        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;
        xhr.addEventListener("readystatechange", function() {
            if(this.readyState === 4) {
                console.log(this.getAllResponseHeaders())
                const tokenHeader = this.getAllResponseHeaders().match(/token:\s[a-zA-z0-9\.\!\?\_\-]+/);
                const usernameHeader = this.getAllResponseHeaders().match(/username:\s[а-яА-ЯёЁa-zA-z0-9\.\!\?\_\-]+/);
                if (tokenHeader && usernameHeader) {
                    localStorage.setItem("token", tokenHeader[0].replace("token: ", ""))
                    localStorage.setItem("username", usernameHeader[0].replace("username: ", ""))
                } else setError(true)
            }
        });
        xhr.open("POST", url);
        xhr.send(JSON.stringify(body));
    }

    return (
        <div className={"container"}>
            <div className={"row justify-content-center"}>
                <div className={"loginWrapper col-4 "}>
                    <p className={"headerLogin"}>Please, Login</p>
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