import { useAuth } from "@/context/auth.context";
import React, { useState } from "react";

const Navbar = () => {
  const [open, setOpen] = useState(false);
  const { isAuthenticated, logout } = useAuth();

  return (
    <header className={`flex w-full items-center bg-white dark:bg-dark`}>
      <div className="container">
        <div className="relative -mx-4 flex items-center justify-between">
          <div className="w-60 max-w-full px-4">
            <div className="ml-5 mb-2 text-2xl font-semibold text-dark dark:text-white">TaskFlow</div>
          </div>
          <div className="flex w-full items-center justify-between px-4">
            <div>
              <button
                onClick={() => setOpen(!open)}
                id="navbarToggler"
                className={` ${open && "navbarTogglerActive"
                  } absolute right-4 top-1/2 block -translate-y-1/2 rounded-lg px-3 py-[6px] ring-primary focus:ring-2 lg:hidden`}
              >
                <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
              </button>
              <nav
                id="navbarCollapse"
                className={`absolute right-4 top-full w-full max-w-[250px] rounded-lg bg-white px-6 py-5 shadow dark:bg-dark-2 lg:static lg:block lg:w-full lg:max-w-full lg:shadow-none lg:dark:bg-transparent ${!open && "hidden"
                  } `}
              >
                {
                  isAuthenticated
                    ? <ul className="block lg:flex">
                      <ListItem NavLink="/#">Dashboard</ListItem>
                      <ListItem NavLink="/#">Projects</ListItem>
                      <ListItem NavLink="/#">Profile</ListItem>
                    </ul>
                    : <ul className="block lg:flex">
                      <ListItem NavLink="/#">Home</ListItem>
                    </ul>
                }
              </nav>
            </div>
            {
              isAuthenticated
                ? <div className="hidden justify-end pr-16 sm:flex lg:pr-0" onClick={() => logout()}>
                  Logout
                </div>
                : <div className="hidden justify-end pr-16 sm:flex lg:pr-0">
                  <a
                    href="/#"
                    className="px-7 py-3 text-base font-medium text-dark hover:text-primary dark:text-white"
                  >
                    Sign in
                  </a>

                  <a
                    href="/#"
                    className="rounded-md bg-primary px-7 py-3 text-base font-medium text-white hover:bg-primary/90"
                  >
                    Sign Up
                  </a>
                </div>
            }
          </div>
        </div>
      </div>
    </header>
  );
};

export default Navbar;

const ListItem = ({ children, NavLink }: any) => {
  return (
    <>
      <li>
        <a
          href={NavLink}
          className="flex py-2 text-base font-medium text-body-color hover:text-dark dark:text-dark-6 dark:hover:text-white lg:ml-12 lg:inline-flex"
        >
          {children}
        </a>
      </li>
    </>
  );
};
