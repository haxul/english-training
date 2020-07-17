import React, {useState} from 'react';
import './App.css';
import {Header} from "./frame/header";
import {Sidebar} from "./frame/sidebar"

import {AddWordPage} from "./pages/addWord/addWordPage"
import {TrainWaterfall} from "./pages/trainWaterfall/trainWaterFall"
import {AppContext} from "./appContext"

function App() {
    const defaultUsername = "guest"
    const defaultSideBarButtons = [{
        name: "Add word"
    }, {
        name: "Train"
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
                    {(activePage === "Add word") ? <AddWordPage/> :
                        (activePage === "Train") ? <TrainWaterfall/>
                            : "nothing"}
                </div>
            </div>
        </AppContext.Provider>
    );
}

export default App;
