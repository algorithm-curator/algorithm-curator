import React from "react";
import { Container, HeaderWrapper } from "./styles";
import Logo from "./Logo";
import LogOff from "./LogOff";
import LogOn from "./LogOn";

function Header() {
	return (
		<Container>
			<HeaderWrapper>
				<Logo />
				<LogOff />
				{/* <LogOn /> */}
			</HeaderWrapper>
		</Container>
	);
}

export default Header;
