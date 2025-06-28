/* eslint-disable react-refresh/only-export-components */
import React, { useState, useContext, } from "react";
import { AuthContext } from "../context/AuthContext";
import CreateClientForm from "../components/CreateClientForm";
import CreateBankAccountForm from "../components/CreateBankAccountForm";
import { FaUserPlus, FaUniversity, FaSignOutAlt } from "react-icons/fa";
import { useNavigate } from "react-router-dom"; 

export default function AgentDashboard() {
  const [activeTab, setActiveTab] = useState("createClient");
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/"); // React router redirect
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      <aside className="w-64 bg-white shadow-md flex flex-col">
        <div className="p-6 border-b">
          <h2 className="text-xl font-bold text-blue-700">Agent Guichet</h2>
        </div>
        <nav className="flex flex-col flex-grow p-4 space-y-2">
          <button
            onClick={() => setActiveTab("createClient")}
            className={`flex items-center gap-3 px-4 py-2 rounded hover:bg-blue-100 transition ${
              activeTab === "createClient" ? "bg-blue-200 font-semibold" : ""
            }`}
          >
            <FaUserPlus size={20} />
            Créer Client
          </button>

          <button
            onClick={() => setActiveTab("createAccount")}
            className={`flex items-center gap-3 px-4 py-2 rounded hover:bg-blue-100 transition ${
              activeTab === "createAccount" ? "bg-blue-200 font-semibold" : ""
            }`}
          >
            <FaUniversity size={20} />
            Nouveau Compte
          </button>

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
        {activeTab === "createClient" && <CreateClientForm />}
        {activeTab === "createAccount" && <CreateBankAccountForm />}
      </main>
    </div>
  );
}