/* eslint-disable react-refresh/only-export-components */
import React, { useState, useContext, useEffect } from "react";
import { AuthContext } from "../context/AuthContext";
import { FaSignOutAlt, FaCreditCard } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import TransferForm from "../components/TransferForm";

export default function ClientDashboard() {
  const { logout, token } = useContext(AuthContext);
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState("default");

  const [profile, setProfile] = useState(null);
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [operations, setOperations] = useState([]);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  useEffect(() => {
    fetch("http://localhost:8082/api/accounts/my-accounts", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => res.json())
      .then(setAccounts)
      .catch(console.error);
  }, [token]);

  useEffect(() => {
    fetch("http://localhost:8082/api/clients/profile", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then(res => res.json())
      .then(setProfile)
      .catch(console.error);
  }, [token]);

  const fetchOperations = (rib) => {
    fetch(`http://localhost:8082/api/accounts/operations?rib=${rib}&page=0&size=10`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => res.json())
      .then((data) => setOperations(data.content || []))
      .catch(console.error);
  };

  const handleSelectAccount = (account) => {
    setSelectedAccount(account);
    setActiveTab("accounts");
    fetchOperations(account.rib);
  };

  return (
    <div className="flex min-h-screen bg-gray-50">
      <aside className="w-64 bg-white shadow-md flex flex-col">
        <div className="p-6 border-b">
          <h2 className="text-xl font-bold text-blue-700">Mon espace client</h2>
        </div>
        <nav className="flex flex-col flex-grow p-4 space-y-2">
          <div>
            {accounts.map((account) => (
              <button
                key={account.rib}
                onClick={() => handleSelectAccount(account)}
                className={`flex items-center gap-3 px-4 py-2 rounded hover:bg-blue-100 transition ${
                  selectedAccount?.rib === account.rib && activeTab === "accounts"
                    ? "bg-blue-200 font-semibold"
                    : ""
                }`}
              >
                <FaCreditCard size={18} />
                {account.rib.slice(-6)} - {account.balance.toFixed(2)} MAD
              </button>
            ))}
            <button
              onClick={() => {
                setSelectedAccount(null);
                setActiveTab("transfer");
              }}
              className={`flex items-center gap-3 px-4 py-2 mt-4 rounded hover:bg-blue-100 transition ${
                activeTab === "transfer" ? "bg-blue-200 font-semibold" : ""
              }`}
            >
              <FaCreditCard size={18} />
              Nouveau Virement
            </button>
          </div>

          <button
            onClick={handleLogout}
            className="mt-auto flex items-center gap-3 px-4 py-2 rounded hover:bg-red-100 text-red-600 font-semibold"
          >
            <FaSignOutAlt size={20} />
            Déconnexion
          </button>
        </nav>
      </aside>

      <main className="flex-grow p-8 max-w-4xl mx-auto">
        {activeTab === "accounts" && selectedAccount ? (
          <>
            <h2 className="text-2xl font-semibold text-gray-800 mb-4">
              Compte RIB: {selectedAccount.rib}
            </h2>
            <p><strong>Solde:</strong> {selectedAccount.balance.toFixed(2)} MAD</p>
            <p><strong>Status:</strong> {selectedAccount.status}</p>

            <h3 className="mt-6 mb-2 text-xl font-semibold text-gray-700">Dernières opérations</h3>
            <ul className="space-y-4">
              {operations.map((op, index) => (
                <li key={index} className="bg-white p-4 rounded shadow">
                  <p className="font-medium">{op.label}</p>
                  <p className="text-sm text-gray-600">
                    {op.type} | {op.amount.toFixed(2)} MAD |{" "}
                    {new Date(op.operationDate).toLocaleString()}
                  </p>
                </li>
              ))}
              {operations.length === 0 && (
                <p className="text-gray-500">Aucune opération trouvée.</p>
              )}
            </ul>
          </>
        ) : activeTab === "transfer" ? (
          <TransferForm
            accounts={accounts}
            selectedAccount={selectedAccount}
            token={token}
          />
        ) : profile ? (
          <div className="bg-white p-6 rounded shadow text-gray-800">
            <h2 className="text-2xl font-bold mb-2">
              Bienvenue, {profile.firstName} {profile.lastName} 👋
            </h2>
            <p className="mb-2"><strong>CIN:</strong> {profile.identityNumber}</p>
            <p className="mb-2"><strong>Email:</strong> {profile.email}</p>
            <p className="mb-2"><strong>Date de naissance:</strong> {profile.birthDate}</p>
            <p className="mb-2"><strong>Adresse postale:</strong> {profile.postalAddress}</p>
            <p className="text-sm text-gray-500 mt-4">
              Sélectionnez un compte à gauche pour voir les détails ou effectuez un virement.
            </p>
          </div>
        ) : (
          <p className="text-gray-600">Chargement du profil...</p>
        )}
      </main>
    </div>
  );
}
