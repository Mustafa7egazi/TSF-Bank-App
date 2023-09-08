package com.example.tsfbanking.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.tsfbanking.R
import com.example.tsfbanking.databinding.FragmentHomeBinding
import com.example.tsfbanking.pojo.Account
import com.example.tsfbanking.reposiory.Repository
import com.example.tsfbanking.room.dao.AccountDao
import com.example.tsfbanking.room.database.AccountsDatabase
import com.example.tsfbanking.viewmodel.BankViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        binding.viewAllCustomerBtn.setOnClickListener {

            //TODO:: Dummy data creation

            /* val dao1 = AccountsDatabase.getDatabase(requireContext()).accountDao()
             val dao2 = AccountsDatabase.getDatabase(requireContext()).transactionDao()

              val repo = Repository(dao1,dao2)

              val ac1 = Account(0,"Mustafa Hegazy","7egz@test.com",20000.0)
              val ac2 = Account(0,"Ahmed Mosaad","mosaad@test.com",6000.0)
              val ac3 = Account(0,"Khaled Samy","smsm@test.com",4000.0)
              val ac4 = Account(0,"Doaa Abo-Shosha","shosh@test.com",5000.0)
              val ac5 = Account(0,"Mahmoud Hamada","hamada@test.com",80500.0)
              val ac6 = Account(0,"Salma Saleh","solh@test.com",9000.0)
              val ac7 = Account(0,"Eslam Sallam","7egz@test.com",3000.0)
              val ac8 = Account(0,"Said Hamed","said@test.com",2000.0)
              val ac9 = Account(0,"Ashraf Safy","sfsf@test.com",11000.0)
              val ac10 = Account(0,"Nora Ahmed","7egz@test.com",20000.0)

              val list = arrayListOf(ac1,ac2,ac3,ac4,ac5,ac6,ac7,ac8,ac9,ac10)
              for (i in list){
                  lifecycleScope.launch(Dispatchers.IO){
                      repo.createNewAccount(i)
                      withContext(Dispatchers.Main){
                          Toast.makeText(requireContext(),"$i created",Toast.LENGTH_SHORT).show()
                      }
                  }
              }
              */

            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_customersFragment)
        }

        return binding.root
    }
}