import React, { ButtonHTMLAttributes } from 'react'
import classes from './Button.module.scss'
type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
    outline? :boolean
    size?: "small" | "medium" | "large"
}
export default function Button({outline,size = "large",className, children,...otherProps}: ButtonProps) {
  return (
    <button {...otherProps} className={`${classes.root} ${outline ? classes.outline : ''} ${classes[size]} ${className}`}>
        {children}
    </button>
  )
}
