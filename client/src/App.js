import React, {useState} from 'react';
import './App.css';
import {Header} from "./frame/header";
import {Sidebar} from "./frame/sidebar"
import {AppContext} from "./appContext"
import pages from "./pages/pages"
import {RegistrationPage} from "./pages/registrationPage/registrationPage"

function App() {
    const token = localStorage.getItem("token")

    const defaultUsername = "guest"
    const buttonsName = Object.keys(pages)
    const [activePage, setActivePage] = useState("")
    const [username, setUsername] = useState(defaultUsername)
    const [sideBarButtons, setSideBarButtons] = useState(buttonsName)

    if (!token) return <RegistrationPage/>

    return (
        <AppContext.Provider value={{setActivePage}}>
            <div className="App">
                <Header username={username}/>
                <div className="d-flex">
                    <Sidebar buttons={sideBarButtons}/>
                    { pages[activePage] }
                </div>
            </div>
        </AppContext.Provider>
    );
}

export default App;
