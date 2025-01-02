import { Outlet } from 'react-router-dom'
import classes from './ApplicationLayout.module.scss'
import Header from '../../../../components/Header/Header'
export default function ApplicationLayout() {
  return (
    <div className={classes.root}>
        <Header></Header>
        <main>
            <Outlet/>
        </main>
    </div>
  )
}
