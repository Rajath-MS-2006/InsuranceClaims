import React from 'react';
import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { BrowserRouter } from 'react-router-dom';
import { AuthContext } from '../AuthContext';
import App from '../App';

// Simple mock for components that might be too complex for a base test
vi.mock('../pages/Landing', () => ({
  default: () => <div data-testid="landing-page">Landing Page</div>,
}));

const mockAuthContext = {
  user: null,
  login: vi.fn(),
  logout: vi.fn(),
};

describe('App Component', () => {
  it('renders landing page when not logged in', () => {
    render(
      <AuthContext.Provider value={mockAuthContext}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </AuthContext.Provider>
    );
    
    expect(screen.getByText(/Explainable Claims/i)).toBeInTheDocument();
    expect(screen.getByTestId('landing-page')).toBeInTheDocument();
  });

  it('renders navbar correctly', () => {
    render(
      <AuthContext.Provider value={mockAuthContext}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByRole('navigation')).toBeInTheDocument();
  });
});
