import { useState } from 'react';
import { useAuthentication } from '../../features/authentication/contexts/AuthenticationContextProvider'
import Input from '../Input/Input'
import classes from './Header.module.scss'
import { NavLink } from 'react-router-dom'
export default function Header() {
    const { user } = useAuthentication();
    const [showProfileMenu,setShowProfileMenu] = useState(false)
    const [showNavigationMenu,setShowNavigationMenu] = useState(
        window.innerWidth > 1080 ? true: false
    )
    return (

        <header className={classes.root}>
            Header
            <div className={classes.container}>
                <div className={classes.left}>
                    <NavLink to='/'>
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            fill="currentColor"
                            className={classes.logo}
                        >
                            <path d="M20.5 2h-17A1.5 1.5 0 002 3.5v17A1.5 1.5 0 003.5 22h17a1.5 1.5 0 001.5-1.5v-17A1.5 1.5 0 0020.5 2zM8 19H5v-9h3zM6.5 8.25A1.75 1.75 0 118.3 6.5a1.78 1.78 0 01-1.8 1.75zM19 19h-3v-4.74c0-1.42-.6-1.93-1.38-1.93A1.74 1.74 0 0013 14.19a.66.66 0 000 .14V19h-3v-9h2.9v1.3a3.11 3.11 0 012.7-1.4c1.55 0 3.36.86 3.36 3.66z"></path>
                        </svg>
                    </NavLink>
                    <Input placeholder="Search" size={"medium"}></Input>
                </div>
                <div className={classes.right}>
                    <button
                        className={classes.toggle}
                        onClick={() => {
                            setShowNavigationMenu((prev) => !prev);
                            setShowProfileMenu(false);
                        }}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512" fill="currentColor">
                            <path d="M0 96C0 78.3 14.3 64 32 64l384 0c17.7 0 32 14.3 32 32s-14.3 32-32 32L32 128C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32l384 0c17.7 0 32 14.3 32 32s-14.3 32-32 32L32 288c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32L32 448c-17.7 0-32-14.3-32-32s14.3-32 32-32l384 0c17.7 0 32 14.3 32 32z" />
                        </svg>
                        <span>Menu</span>
                    </button>
                    {
                        user? (
                            <Profile>
                                setShowNavigationMenu = {setShowNavigationMenu}
                                showProfileMenu = {showProfileMenu}
                                setShowProfileMenu = {setShowProfileMenu}
                            </Profile>
                        ) : null
                    }
            </div>
        </div>
    </header>
  )
}
