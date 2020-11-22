import { useAuth0 } from '@auth0/auth0-react';
import React from 'react';

function Header() {
	const { user, isAuthenticated, loginWithRedirect, logout } = useAuth0();
	return (
		<div className="header">
			<div>santa post</div>
			<div>
				{isAuthenticated ? (
					<div>
						<span>{user.email}</span>
						<button
							onClick={() => logout({ returnTo: window.location.origin })}
						>
							Logout
						</button>
					</div>
				) : (
					<button onClick={loginWithRedirect}>Login</button>
				)}
			</div>
		</div>
	);
}

export default Header;
