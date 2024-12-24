import React, { ButtonHTMLAttributes } from 'react'
import classes from './Button.module.scss'
type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
    outline? :boolean
}
export default function Button({outline,children,...otherProps}: ButtonProps) {
  return (
    <button {...otherProps} className={`${classes.root} ${outline ? classes.outline : ''}`}>
        {children}
    </button>
  )
}
