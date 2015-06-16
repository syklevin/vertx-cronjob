require 'vertx/util/utils.rb'
# Generated from com.glt.cronjob.JobService
module Cronjob
  #  Created by levin on 5/22/2015.
  class JobService
    # @private
    # @param j_del [::Cronjob::JobService] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Cronjob::JobService] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @return [::Cronjob::JobService]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::ComGltCronjob::JobService.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Cronjob::JobService)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [String] address 
    # @return [::Cronjob::JobService]
    def self.create_proxy(vertx=nil,address=nil)
      if vertx.class.method_defined?(:j_del) && address.class == String && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::ComGltCronjob::JobService.java_method(:createProxy, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class]).call(vertx.j_del,address),::Cronjob::JobService)
      end
      raise ArgumentError, "Invalid arguments when calling create_proxy(vertx,address)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    # @param [Hash{String => Object}] jobDescriptor 
    # @yield 
    # @return [self]
    def schedule(jobDescriptor=nil)
      if jobDescriptor.class == Hash && block_given?
        @j_del.java_method(:schedule, [Java::IoVertxCoreJson::JsonObject.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_json_object(jobDescriptor),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling schedule(jobDescriptor)"
    end
    # @param [Hash{String => Object}] jobDescriptor 
    # @yield 
    # @return [self]
    def unschedule(jobDescriptor=nil)
      if jobDescriptor.class == Hash && block_given?
        @j_del.java_method(:unschedule, [Java::IoVertxCoreJson::JsonObject.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_json_object(jobDescriptor),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling unschedule(jobDescriptor)"
    end
  end
end
