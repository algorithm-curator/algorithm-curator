/* eslint-disable @typescript-eslint/no-unused-vars */
import React from "react";
import { isLoggedState } from "stores/Auth";
import { useRecoilState } from "recoil";
import { Container, HeaderWrapper } from "./styles";
import Logo from "./Logo";
import LogOff from "./LogOff";
import LogOn from "./LogOn";

function Header() {
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	return (
		<Container>
			<HeaderWrapper>
				<Logo />
				{isLogged ? <LogOn /> : <LogOff />}
			</HeaderWrapper>
		</Container>
	);
}

export default Header;
