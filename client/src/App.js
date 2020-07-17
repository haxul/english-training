import React, {useState} from 'react';
import './App.css';
import {Header} from "./frame/header";
import {Sidebar} from "./frame/sidebar"
import {AppContext} from "./appContext"
import pages from "./pages/pages"

function App() {
    const defaultUsername = "guest"
    const defaultSideBarButtons = [{
        name: "Add word"
    }, {
        name: "Training"
    }, {
        name: "Vocabulary"
    }
    ]
    const [activePage, setActivePage] = useState("")
    const [username, setUsername] = useState(defaultUsername)
    const [sideBarButtons, setSideBarButtons] = useState(defaultSideBarButtons)
    return (
        <AppContext.Provider value={{
            setActivePage
        }}>
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
